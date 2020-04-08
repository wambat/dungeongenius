(ns dg.shared.game-logic.dungeon.layout
  (:require [dg.shared.game-logic.actors.index :as aidx]
            [dg.shared.game-logic.dungeon.query :as dq]
            #?(:clj [taoensso.timbre :refer [log trace debug info warn error]])
            #?(:cljs [taoensso.timbre :refer-macros [log trace debug info warn error]])
            ))

(def example {:actors-index aidx/idx
              :board {:size {:x 20
                             :y 10}}
              :actors [(aidx/->make {:position {:x 1 :y 0}
                                     :type :floor})
                       (aidx/->make {:position {:x 1 :y 1}
                                     :type :floor})
                       (aidx/->make {:position {:x 2 :y 1}
                                     :type :floor})
                       (aidx/->make {:position {:x 2 :y 2}
                                     :type :floor})

                       (aidx/->make {:position {:x 3 :y 1}
                                     :type :floor})

                       (aidx/->make {:position {:x 2 :y 1}
                                     :type :door})
                       (aidx/->make {:position {:x 1 :y 1}
                                     :type :player})]})

(defn make-move
  "Advances the world one turn"
  [{:keys [world action] :as params}]
  (let [ctls (first (dq/get-actors-by-trait world :controllable))
        ctl-trait (dq/get-actor-traits world ctls)
        updated-ctls ((get-in ctl-trait [:controllable :call])
                      world ctls action)]
    (info [:CONTRollables
           ctls
           ctl-trait

           updated-ctls])
    {:world (dq/update-actor-by-id world
                                (:id ctls)
                               updated-ctls)
     :events []}))
