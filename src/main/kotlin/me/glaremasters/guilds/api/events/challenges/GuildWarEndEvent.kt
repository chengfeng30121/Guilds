/*
 * MIT License
 *
 * Copyright (c) 2023 Glare
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package me.glaremasters.guilds.api.events.challenges

import me.glaremasters.guilds.guild.Guild
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

/**
 * An event that is called when a guild war ends.
 *
 * @property challenger the guild who initiated the war
 * @property defender the guild who was challenged
 * @property winner the guild that won the war
 *
 * @constructor Creates a new GuildWarEndEvent with the given challenger, defender and winner.
 */
class GuildWarEndEvent(val challenger: Guild, val defender: Guild, val winner: Guild) : Event() {


    /**
     * Gets a list of all registered handlers for this event.
     *
     * @return the handlers list
     */
    override fun getHandlers(): HandlerList {
        return handlerList
    }

    companion object {
        /**
         * The static list of event handlers for this event type.
         */
        val handlerList = HandlerList()
    }
}
