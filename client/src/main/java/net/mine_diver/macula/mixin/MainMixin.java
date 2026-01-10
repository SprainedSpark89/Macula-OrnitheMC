package net.mine_diver.macula.mixin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Redirect;

import joptsimple.OptionException;
import joptsimple.OptionParser;
import joptsimple.OptionSet;

import org.spongepowered.asm.mixin.injection.At;
import net.minecraft.client.main.Main;

@Mixin(Main.class)
public class MainMixin {

	@Redirect(method = "main", at = @At(value = "INVOKE", target = "Ljoptsimple/OptionParser;parse([Ljava/lang/String;)Ljoptsimple/OptionSet;", remap=false))
	private static OptionSet catchBadArgs(OptionParser parser, String[] args) {
		boolean first = true;
		List<String> list = new ArrayList<>(Arrays.asList(args));
		while (true) {
			try {
				if (first) {
					first = false;
					return parser.parse(args);
				} else {
					return parser.parse(list.toArray(new String[0]));
				}
			} catch (OptionException e) {
				StackTraceElement[] sTE = e.getStackTrace();
				String[] split = e.getMessage().split("'");
				String arg = split[1];

				for (int i = 0; i < list.size(); i++) {
					if (list.get(i).equals("--" + arg)) {
						list.remove(i); // flag
						list.remove(i); // value
						break;
					}
				}

			}
		}
	}
}
