module HW1.T4 
  ( tfoldr
  , treeToList
  ) where

import HW1.T3

tfoldr :: (a -> b -> b) -> b -> Tree a -> b
tfoldr f val Leaf 						   = val
tfoldr f val (Branch size left cur right) = tfoldr f (f cur (tfoldr f val right)) left

treeToList :: Tree a -> [a]
treeToList = tfoldr (:) []