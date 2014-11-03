package latmod.core;
import latmod.core.block.BlockLM;
import latmod.core.item.*;
import latmod.core.recipes.LMRecipes;
import latmod.core.tile.TileLM;
import latmod.core.util.FastList;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.*;
import net.minecraft.util.*;

import org.apache.logging.log4j.*;

import cpw.mods.fml.relauncher.*;

public class LMMod<C extends LMConfig, R extends LMRecipes>
{
	public final String modID;
	public final String assets;
	
	public final FastList<BlockLM> blocks;
	public final FastList<IItemLM> items;
	
	public final Logger logger;
	private LMConfig config;
	private LMRecipes recipes;
	
	public LMMod(String s, C c, R r)
	{
		modID = s;
		
		assets = s.toLowerCase() + ":";
		
		blocks = new FastList<BlockLM>();
		items = new FastList<IItemLM>();
		
		logger = LogManager.getLogger(modID);
		logger.info("Loading mod: " + modID);
		
		config = c;
		recipes = r;
	}
	
	@SuppressWarnings("unchecked") public C config() { return (C)config; }
	@SuppressWarnings("unchecked") public R recipes() { return (R)recipes; }
	
	public ResourceLocation getLocation(String s)
	{ return new ResourceLocation(assets + s); }
	
	public CreativeTabs createTab(final String s, final ItemStack icon)
	{
		CreativeTabs tab = new CreativeTabs(assets + s)
		{
			@SideOnly(Side.CLIENT)
			public ItemStack getIconItemStack()
			{ return icon; }
			
			@SideOnly(Side.CLIENT)
			public Item getTabIconItem()
			{ return getIconItemStack().getItem(); }
		};
		
		return tab;
	}
	
	public final String getBlockName(String s)
	{ return assets + "tile." + s; }
	
	public final String getItemName(String s)
	{ return assets + "item." + s; }
	
	public final String translate(String s, Object... args)
	{ if(args == null || args.length == 0) return StatCollector.translateToLocal(assets + s);
	else return StatCollector.translateToLocalFormatted(assets + s, args); }
	
	public void addItem(IItemLM i)
	{ LatCoreMC.addItem(i.getItem(), i.getItemID()); items.add(i); }

	public void addBlock(BlockLM b, Class<? extends ItemBlockLM> c)
	{ LatCoreMC.addBlock(b, c, b.blockName); blocks.add(b); }

	public void addBlock(BlockLM b)
	{ addBlock(b, ItemBlockLM.class); }

	public void addTile(Class<? extends TileLM> c, String s)
	{ LatCoreMC.addTileEntity(c, modID + '.' + s); }
	
	public void addEntity(Class<? extends Entity> c, String s, int id)
	{ LatCoreMC.addEntity(c, s, id, modID); }
	
	public void onPostLoaded()
	{
		for(int i = 0; i < items.size(); i++)
			items.get(i).onPostLoaded();
		
		for(int i = 0; i < blocks.size(); i++)
			blocks.get(i).onPostLoaded();
	}
	
	public void loadRecipes()
	{
		for(int i = 0; i < items.size(); i++)
			items.get(i).loadRecipes();
		
		for(int i = 0; i < blocks.size(); i++)
			blocks.get(i).loadRecipes();
		
		if(recipes != null) recipes.loadRecipes();
	}
}