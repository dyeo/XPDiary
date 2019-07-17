package net.dyeo.xpdiary

import net.dyeo.xpdiary.common.network.GuiHandler
import net.dyeo.xpdiary.network.XPBalanceMessageHandler
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.SidedProxy
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import net.minecraftforge.fml.common.network.NetworkRegistry
import net.minecraftforge.fml.relauncher.Side

const val modgroup = "net.dyeo"
const val modid = "xpdiary"
const val name = "XP Diary"
const val version = "\${version}"
const val modLanguageAdapter = "net.shadowfacts.forgelin.KotlinAdapter"
const val dependencies = "required-before:forgelin@[1.6.0,);"
const val clientProxyClass = "net.dyeo.xpdiary.proxy.ClientProxy"
const val serverProxyClass = "net.dyeo.xpdiary.proxy.ServerProxy"

@Mod(modid=modid, name=name, version=version, modLanguageAdapter=modLanguageAdapter, dependencies=dependencies)
object XPDiary
{
    val networkChannel = NetworkRegistry.INSTANCE.newSimpleChannel(modid)!!

    enum class NetworkDiscriminators
    {
        XPBalance
    }

    @JvmStatic
    @Mod.EventHandler
    fun preInit(event: FMLPreInitializationEvent)
    {
    }

    @JvmStatic
    @Mod.EventHandler
    fun init(event: FMLInitializationEvent)
    {
        XPBalanceMessageHandler.registerMessage(Side.CLIENT)
        XPBalanceMessageHandler.registerMessage(Side.SERVER)
        NetworkRegistry.INSTANCE.registerGuiHandler(XPDiary, GuiHandler)
    }

    @JvmStatic
    @Mod.EventHandler
    fun postInit(event: FMLPostInitializationEvent)
    {
    }
}