(defproject naipmoro/qrng "0.1.0"
  :description "random number generation via the ANU Quantum Random Numbers Server"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"} 
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [clj-http "0.6.5"]]
  :profiles {:dev {:dependencies [[criterium "0.3.1"]]}
             :1.3 {:dependencies [[org.clojure/clojure "1.3.0"]]}
             :1.4 {:dependencies [[org.clojure/clojure "1.4.0"]]}})
