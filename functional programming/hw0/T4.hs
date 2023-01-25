module HW0.T4 ( repeat'
              , map'
              , fib
              , fac
              ) where

import Data.Function (fix)
import Numeric.Natural

repeat' :: a -> [a]
repeat' x = fix (x:)

map' :: (a -> b) -> [a] -> [b]
map' = fix (\rec f array ->
  case array of
    []     -> []
    hd : tl -> (f hd) : (rec f tl))

fib :: Natural -> Natural
fib = fix (\rec a b n -> if n > 0 then rec b (a + b) (n - 1) else a) 0 1 

fac :: Natural -> Natural
fac = fix (\rec n -> if n == 1 || n == 0 then 1 else n * rec (n - 1))