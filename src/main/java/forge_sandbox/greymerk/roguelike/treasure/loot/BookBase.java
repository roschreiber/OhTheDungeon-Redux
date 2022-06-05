package forge_sandbox.greymerk.roguelike.treasure.loot;

import java.util.ArrayList;
import java.util.List;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

//import net.minecraft.init.Items;
//import net.minecraft.item.ItemStack;
//import net.minecraft.nbt.NBTTagList;
//import net.minecraft.nbt.NBTTagString;
//import net.minecraft.util.text.ITextComponent;
//import net.minecraft.util.text.TextComponentString;

public class BookBase implements IBook {

	protected List<String> pages;
	protected String author;
	protected String title;

	public BookBase() {
		this.pages = new ArrayList<>();
	}

	public BookBase(String author, String title) {
		this.pages = new ArrayList<>();
		this.author = author;
		this.title = title;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public final void addPage(String page) {
		this.pages.add(page);
	}

	@SuppressWarnings("deprecation")
	@Override
	public ItemStack get() {
		ItemStack book = new ItemStack(Material.WRITTEN_BOOK, 1);
		BookMeta bookMeta = (BookMeta) book.getItemMeta();
		ComponentBuilder cb = new ComponentBuilder(this.title == null ? "Book" : this.title);
		bookMeta.spigot().addPage(cb.create());
		String array_page[] = new String[pages.size()];
		for (int i = 0; i < array_page.length; i++) {
			array_page[i] = pages.get(i);
		}
		bookMeta.addPage(array_page);
		bookMeta.setAuthor(this.author == null ? "Anonymous" : this.author);
		bookMeta.setTitle(this.title == null ? "Book" : this.title);

		book.setItemMeta(bookMeta);

		return book;
	}
}
