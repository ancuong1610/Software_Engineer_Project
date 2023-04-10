package de.hda.fbi.efussgaengerzone;

import com.samskivert.mustache.Mustache;
import com.samskivert.mustache.Template;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

@SpringBootApplication
public class EfussgaengerzoneApplication {

	public static void main(String[] args) {
		SpringApplication.run(EfussgaengerzoneApplication.class, args);
	}

}

@ControllerAdvice
class LayoutAdvice {

	private final Mustache.Compiler compiler;

	public LayoutAdvice(Mustache.Compiler compiler) {
		this.compiler = compiler;
	}

	@ModelAttribute("title")
	public Mustache.Lambda menu(@ModelAttribute Layout layout) {
		return (frag, out) ->
			layout.title = frag.execute();
	}

	@ModelAttribute("layout")
	public Mustache.Lambda layout(Map<String, Object> model) {
		return new Layout(compiler);
	}
}

class Layout implements Mustache.Lambda {

	String title = "eFußgängerzone";

	String body;

	private final Mustache.Compiler compiler;

	public Layout(Mustache.Compiler compiler) {
		this.compiler = compiler;
	}

	@Override
	public void execute(Template.Fragment frag, Writer out) throws IOException {
		body = frag.execute();
		compiler.compile("{{>layout}}").execute(frag.context(), out);
	}

}