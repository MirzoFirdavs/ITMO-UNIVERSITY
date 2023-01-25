module HW1.T7 
  ( ListPlus (..)
  , DotString (..)
  , Inclusive (..)
  , Fun (..)
  ) where

data ListPlus a = a :+ ListPlus a | Last a deriving (Show)
infixr 5 :+

instance Semigroup (ListPlus a) where
  Last x <> list   = x :+ list
  (h :+ t) <> list = h :+ (t <> list)

data Inclusive a b = This a | That b | Both a b deriving (Show)

instance (Semigroup a, Semigroup b) => Semigroup (Inclusive a b)
  where
    (This x) <> (This y)     = This (x <> y)
    (That x) <> (That y)     = That (x <> y)
    (This x) <> (That y)     = Both x y
    (That x) <> (This y)     = Both y x
    (This x) <> (Both y z)   = Both (x <> y) z
    (That x) <> (Both y z)   = Both y (x <> z)
    (Both y z) <> (This x)   = Both (y <> x) z
    (Both y z) <> (That x)   = Both y (z <> x)
    (Both a b) <> (Both c d) = Both (a <> c) (b <> d)

newtype DotString = DS String deriving (Show)

instance Semigroup DotString where
  (DS "") <> x = x
  x <> (DS "") = x
  (DS x) <> (DS y) = DS (x ++ "." ++ y)

instance Monoid DotString where
  mempty = DS ""

newtype Fun a = F (a -> a)  

instance Semigroup (Fun a) where
    (F x) <> (F y) = F  (x . y)

instance Monoid (Fun a) where
    mempty = F id