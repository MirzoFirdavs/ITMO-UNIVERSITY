module HW1.T3 
  ( Tree (..)
  , tsize
  , tdepth
  , tmember
  , tinsert
  , tFromList
  ) where

data Tree a = Leaf | Branch Int (Tree a) a (Tree a) deriving (Show)

tsize :: Tree a -> Int
tsize Leaf                            = 0
tsize (Branch size left parent right) = size

tdepth :: Tree a -> Int
tdepth Leaf                = 0
tdepth (Branch size left parent right) = 1 + max (tdepth left) (tdepth right)

tmember :: Ord a => a -> Tree a -> Bool
tmember value Leaf = False
tmember value (Branch size left cur right) | value == cur = True
                                           | value > cur = tmember value right
                                           | value < cur = tmember value left

mkBranch :: Tree a -> a -> Tree a -> Tree a
mkBranch Leaf t Leaf = Branch 1 Leaf t Leaf
mkBranch Leaf t (Branch size left value right) = Branch (size + 1) Leaf t (Branch size left value right)
mkBranch (Branch size left value right) t Leaf = Branch (size + 1) (Branch size left value right) t Leaf
mkBranch (Branch size1 left1 value1 right1) t (Branch size2 left2 value2 right2) = Branch (size1 + size2 + 1) (Branch size1 left1 value1 right1) t (Branch size2 left2 value2 right2)

tinsert :: Ord a => a -> Tree a -> Tree a
tinsert value Leaf = mkBranch Leaf value Leaf
tinsert value (Branch size left cur right) | value < cur = mkBranch (tinsert value left) cur right 
                                           | value > cur = mkBranch left cur (tinsert value right)
                                           | otherwise   = (Branch size left cur right)

tFromList :: Ord a => [a] -> Tree a     
tFromList [] = Leaf
tFromList (h:t) = foldr tinsert Leaf (h:t)