package net.dyeo.xpdiary.proxy

import net.dyeo.xpdiary.block.BlockDiary
import net.dyeo.xpdiary.modid
import net.dyeo.xpdiary.renderer.TileEntityDiaryRenderer
import net.dyeo.xpdiary.tileentity.TileEntityDiary
import net.minecraft.client.renderer.block.model.ModelResourceLocation
import net.minecraftforge.client.model.ModelLoader
import net.minecraftforge.fml.client.registry.ClientRegistry
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent

class ClientProxy : CommonProxy()
{
    val diaryRenderer = TileEntityDiaryRenderer()

    override fun preInit(event: FMLPreInitializationEvent)
    {
        super.preInit(event)
        registerClientResources()
    }

    private fun registerClientResources()
    {
        ModelLoader.setCustomModelResourceLocation(itemBlockDiary, 0, ModelResourceLocation("$modid:${BlockDiary.name}", "inventory"))
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityDiary::class.java, diaryRenderer)
    }

}