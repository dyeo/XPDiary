package net.dyeo.xpdiary.proxy

import net.dyeo.xpdiary.XPDiary
import net.dyeo.xpdiary.common.network.GuiHandler
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import net.minecraftforge.fml.common.network.NetworkRegistry

open class CommonProxy
{
    open fun preInit(event: FMLPreInitializationEvent)
    {
    }

    open fun init(event: FMLInitializationEvent)
    {
        NetworkRegistry.INSTANCE.registerGuiHandler(XPDiary, GuiHandler)
    }

    open fun postInit(event: FMLPostInitializationEvent)
    {
    }
}