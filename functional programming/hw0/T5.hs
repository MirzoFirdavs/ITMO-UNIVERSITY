module HW0.T5 ( Nat (..)
              , nz
              , ns
              , nplus
              , nmult
              , nFromNatural
              , nToNum
              ) where

import Numeric.Natural

type Nat a = (a -> a) -> a -> a

nz :: Nat a
nz f a = a

ns :: Nat a -> Nat a
ns n f a = f (n f a)

nplus :: Nat a -> Nat a -> Nat a
nplus n1 n2 f a = n2 f (n1 f a)

nmult :: Nat a -> Nat a -> Nat a
nmult n1 n2 f = n1 (n2 f)

nFromNatural :: Natural -> Nat a
nFromNatural a = if a == 0 then nz else ns (nFromNatural (a - 1))

nToNum :: Num a => Nat a -> a
nToNum n = n (+1) 0