(ns dg.shared.game-logic.dungeon.layout
  (:require [dg.shared.game-logic.actors.index :as aidx]))

(def example {:actors-index aidx/idx
              :board {:size {:x 10
                             :y 10}}
              :actors [(aidx/->make {:position {:x 1 :y 1}
                                     :type :floor})
                       (aidx/->make {:position {:x 2 :y 1}
                                     :type :floor})

                       (aidx/->make {:position {:x 3 :y 1}
                                     :type :floor})

                       (aidx/->make {:position {:x 1 :y 1}
                                     :type :player})
                       (aidx/->make {:position {:x 2 :y 1}
                                     :type :door})]})
