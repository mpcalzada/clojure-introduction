(ns record-protocol-multimethod.lesson1
  (:use [clojure.pprint]))

; { 15 { :id 15, :name "Marco"},  23 {:id 23, :name "Blah" } }

(defn add-patient
  [patient patients]
  (if-let [id (:id patient)]
           (assoc patients id patient)
           (throw (ex-info "Patient missing ID" {:patient patient}))))

(defn test-patient-list
  []
  (let [patients {}
        william {:id 15 :name "William" :birthdate "09/18/1981"}
        daniela {:id 20 :name "Daniela" :birthdate "09/18/1981"}
        paul {:id 25 :name "Paul" :birthdate "09/18/1981"}
        ]
    (pprint (add-patient william patients))
    (pprint (add-patient daniela patients))
    (pprint (add-patient paul patients))
    ))

(test-patient-list)

(defrecord Patient [id name birthdate])

(println (->Patient 15 "William" "09/18/1981"))
(pprint (->Patient 15 "William" "09/18/1981"))

(pprint (Patient. 15 "William" "09/18/1981"))
(pprint (Patient. "William" 15 "09/18/1981"))

(pprint (map->Patient {:id 15, :name "William", :birthdate "09/18/1981"}))

(let [william (->Patient 15 "William" "09/18/1981")]
  (println (:id william))
  (println (vals william))
  (println (record? william))
  (println (.name william))
  )

(pprint (map->Patient {:name "William", :birthdate "09/18/1981" :ssn "222"}))
(pprint (assoc (Patient. 15 "William" "09/18/1981") :id 20
                                                    ))
(pprint (= (Patient. 15 "William" "09/18/1981") (Patient. 10 "William" "09/18/1981")))
