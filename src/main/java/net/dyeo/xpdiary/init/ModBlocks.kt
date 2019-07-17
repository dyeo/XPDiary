package net.dyeo.xpdiary.init

import net.dyeo.xpdiary.block.BlockDiary
import net.dyeo.xpdiary.modid
import net.minecraft.block.Block
import net.minecraft.init.Blocks
import net.minecraft.init.Items
import net.minecraft.item.Item
import net.minecraft.item.ItemBlock
import net.minecraft.item.ItemStack
import net.minecraft.util.ResourceLocation
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.registry.GameRegistry
import net.minecraftforge.common.ForgeHooks.getRegistryName
import net.minecraft.client.renderer.block.model.ModelResourceLocation
import net.minecraftforge.client.model.ModelLoader
import net.minecraftforge.client.event.ModelRegistryEvent
import net.minecraftforge.fml.common.thread.SidedThreadGroups.CLIENT
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly



object ModBlocks
{
    val blockDiary = BlockDiary().setUnlocalizedName("diary").setRegistryName("diary") as Block
    var itemBlockDiary = ItemBlock(blockDiary).setRegistryName(blockDiary.registryName!!)

    @Mod.EventBusSubscriber(modid="xpdiary")
    object EventHandlers
    {
        @SubscribeEvent
        fun registerBlocks(event: RegistryEvent.Register<Block>)
        {
            event.registry.register(blockDiary)
        }

        @SubscribeEvent
        fun registerItems(event: RegistryEvent.Register<Item>)
        {
            event.registry.register(itemBlockDiary)
        }

        @SideOnly(Side.CLIENT)
        @SubscribeEvent
        fun onModelRegistry(event: ModelRegistryEvent)
        {
            val item = Item.getItemFromBlock(blockDiary)
            ModelLoader.setCustomModelResourceLocation(item, 0, ModelResourceLocation("xpdiary:diary", "inventory"))
        }
    }

    fun registerCraftingRecipes()
    {
        GameRegistry.addShapedRecipe(ResourceLocation("xpdiary:diary"),
                ResourceLocation(""),
                ItemStack(itemBlockDiary),
                " A ",
                "BCB",
                "CDC",
                'A', Items.WRITABLE_BOOK,
                'B', Items.DIAMOND,
                'C', Blocks.IRON_BLOCK,
                'D', Blocks.LAPIS_BLOCK)
    }
}