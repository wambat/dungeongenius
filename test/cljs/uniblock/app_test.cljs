(ns uniblock.app-test
  (:require-macros [cljs.test :refer [deftest testing is]])
  (:require [cljs.test :as t]
            [uniblock.app :as app]))

(deftest test-arithmetic []
  (is (= (+ 0.1 0.2) 0.30000000000000004) "Something foul is a float."))

(deftest test-arithmetic-2 []
  (is (= (+ 1 2) 3) "Something is ok."))
