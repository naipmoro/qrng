## qrng

A clojure interface to the [ANU Quantum Random Numbers Server](http://qrng.anu.edu.au/index.php).  
The numbers generated are purported to be 'truly random,' based on  
measuring the quantum fluctuations of the vacuum.

## Installation

Available from [Clojars:](https://clojars.org/naipmoro/qrng)  

**Leiningen**  

```clj
[naipmoro/qrng "0.5.1"]
```
## Usage

**(qrand & opts)**

The **:length** keyword determines the number of random numbers to be  
returned. The default is 1.

You have 3 choices for keyword **:type**:  
 __:int8__   (returns integers between 0-255; the default)  
__:int16__   (returns integers between 0-65535)  
__:hex16__   (returns hexadecimal strings between 00-ff)  
  
The **:blocks** keyword is relevant only for type :hex16. It sets  
the hexadecimal block size and must be a number between 1-1024.  
The default is 1.

If keyword **:https** is false, the connection will be established using  
the http protocol. The default is true (https is used).

NOTE:  
If the requested vector is larger than the server's maximum (1024),
a connection pool is used to concat multiple requests.

## Examples

```clj
(require '[naipmoro.qrng.core :refer [qrand]])
```

One random integer between 0-255:  

```clj
(qrand)  
=> [110]
```

6 random integers between 0-255:  
 
```clj
(qrand :length 6)  
=> [67 225 141 118 46 102]
```

4 random integers between 0-65535:  

```clj  
(qrand :length 4, :type :int16)  
=> [24861 25422 60585 58192]
```

6 random hexadecimal strings (00-ff):  

```clj  
(qrand :length 6, :type :hex16)  
=> ["f8" "45" "3b" "78" "06" "20"]
```

5 hexadecimal blocks of size 2 (0000-ffff):  
  
```clj
(qrand :length 5, :type :hex16, :blocks 2)  
=> ["07cb" "fcc6" "411b" "2780" "3a99"]
```

1024 bytes of random hexadecimals as one string:  

```clj
(qrand  :type :hex16, :blocks 1024)  
=> ["554681a2c8a6<..snipped 2024 characters..>16faddb80522"]
```

Of course, the **qrand** function can be used to create random streams of other  
types. For example:

```clj
(defn qrand-bin
  "Returns a lazy sequence of n random bits"
  [n]
  (map #(rem % 2) (qrand :length n)))

(qrand-bin 10)
=> (1 0 0 1 0 1 1 1 0 1)
```

### Acknowledgments

Thanks to the folks at **qrng.anu.edu.au** for allowing the public free access  
to their random number generator.

### License

Distributed under the MIT License.
