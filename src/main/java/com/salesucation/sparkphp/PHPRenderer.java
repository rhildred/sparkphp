package com.salesucation.sparkphp;

import java.io.*;

import com.caucho.quercus.env.Env;
import com.caucho.quercus.env.JavaValue;
import com.caucho.quercus.env.ConstStringValue;
import com.caucho.quercus.page.InterpretedPage;
import com.caucho.quercus.page.QuercusPage;
import com.caucho.quercus.parser.QuercusParser;
import com.caucho.quercus.program.QuercusProgram;
import com.caucho.quercus.program.JavaClassDef;
import com.caucho.quercus.QuercusContext;
import com.caucho.vfs.WriteStream;

public class PHPRenderer {
	protected QuercusContext _quercus;
	protected String sViewDir = "";

	public PHPRenderer() {
		_quercus = new QuercusContext();
		_quercus.init();
		_quercus.start();
	}

	public void setViewDir(String sViewDir) {
		this.sViewDir = sViewDir;
	}

	public String render(String sFile) {
		return this.render(sFile, null);
	}

	public String render(String sFile, Object oJava) {
		String rc = "";
		try {
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			ClassLoader classLoader = PHPRenderer.class.getClassLoader();
			File file = new File(classLoader.getResource("viewWrapper.php").getFile());
			Reader oReader = new FileReader(file);
			QuercusProgram program = QuercusParser.parse(_quercus, null, oReader);

			OutputStreamStream s = new OutputStreamStream(os);
			WriteStream out = new WriteStream(s);

			out.setNewlineString("\n");

			try {
				out.setEncoding("iso-8859-1");
			} catch (Exception e) {
			}
			QuercusPage page = new InterpretedPage(program);

			Env env = new Env(_quercus, page, out, null, null);
			if (oJava != null) {
				JavaClassDef classDef = env.getJavaClassDefinition(oJava.getClass());
				env.setGlobalValue("model", new JavaValue(env, oJava, classDef));
			}
			env.setGlobalValue("filename", new ConstStringValue(sFile));
			env.setGlobalValue("basedir", new ConstStringValue(_quercus.getPwd() + "/" + this.sViewDir));

			try {
				env.start();
				program.execute(env);

			} catch (Exception e) {
				e.printStackTrace();
			}
			out.flushBuffer();
			out.free();
			os.flush();

			rc = os.toString("UTF-8");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return rc;
	}
}