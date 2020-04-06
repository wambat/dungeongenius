(ns dg.client.dungeon.render.text.actors.idx
  (:require
   [dg.client.dungeon.render.text.actors.door :as door]
   [dg.client.dungeon.render.text.actors.floor :as floor]
   [dg.client.dungeon.render.text.actors.player :as player]
   [dg.client.dungeon.render.text.actors.wall :as wall]))

(def calls
  {:door door/draw
   :wall wall/draw
   :player player/draw
   :floor floor/draw})
