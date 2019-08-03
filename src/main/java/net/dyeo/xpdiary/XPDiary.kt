package net.dyeo.xpdiary

import net.dyeo.xpdiary.proxy.CommonProxy
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.SidedProxy
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import net.minecraftforge.fml.common.network.NetworkRegistry

const val modgroup = "net.dyeo"
const val modid = "xpdiary"
const val name = "XP Diary"
const val version = "\${version}"
const val modLanguageAdapter = "net.shadowfacts.forgelin.KotlinAdapter"
const val dependencies = "required-before:forgelin@[1.6.0,);"
const val serverProxyClass = "net.dyeo.xpdiary.proxy.ServerProxy"
const val clientProxyClass = "net.dyeo.xpdiary.proxy.ClientProxy"

@Mod(modid=modid, name=name, version=version, modLanguageAdapter=modLanguageAdapter, dependencies=dependencies)
object XPDiary
{
    val networkChannel = NetworkRegistry.INSTANCE.newSimpleChannel(modid)!!

    enum class NetworkDiscriminators
    {
        XPBalance
    }

    @SidedProxy(clientSide = clientProxyClass, serverSide = serverProxyClass)
    var proxy: CommonProxy? = null

    @JvmStatic
    @Mod.EventHandler
    fun preInit(event: FMLPreInitializationEvent)
    {
        proxy!!.preInit(event)
    }

    @JvmStatic
    @Mod.EventHandler
    fun init(event: FMLInitializationEvent)
    {
        proxy!!.init(event)
    }

    @JvmStatic
    @Mod.EventHandler
    fun postInit(event: FMLPostInitializationEvent)
    {
        proxy!!.postInit(event)
    }
}