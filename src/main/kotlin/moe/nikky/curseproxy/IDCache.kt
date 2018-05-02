package moe.nikky.curseproxy

import aballano.kotlinmemoization.memoize
import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import moe.nikky.curseproxy.CurseUtil.META_URL
import moe.nikky.curseproxy.CurseUtil.getAddon
import voodoo.curse.AddOn
import java.io.File

/**
 * Created by nikky on 01/05/18.
 * @author Nikky
 * @version 1.0
 */

object IDCache {

    val mapper = jacksonObjectMapper() // Enable Json parsing
            .registerModule(KotlinModule()) // Enable Kotlin support
            .configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true)
//            .configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS, true)

    val idFile = File("ids.json")
    val idMap: MutableMap<String, Int> = load()


    fun load(): MutableMap<String, Int> {
        return if(idFile.exists()) {
            mapper.readValue(idFile)
        } else mutableMapOf()
    }

    fun get(): Map<String, Int> = idMap

    fun get(id: Int): String {
        val name = idMap.filterValues { it == id }.keys.toList().firstOrNull()
        if (name != null) return name

        val url = "$META_URL/api/v2/direct/GetAddOn/$id"
        val addon = getAddon(id) ?: throw IllegalStateException("cannot connect to $url")

        idMap[addon.name] = addon.id

        mapper.writeValue(idFile, idMap)

        return addon.name
    }



}