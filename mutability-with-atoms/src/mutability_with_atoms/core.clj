(ns mutability-with-atoms.core
  (:use [clojure pprint])
  (:require
    [mutability-with-atoms.model :as h.model]))

(let [my-hospital h.model/new-hospital]
  (pprint my-hospital))