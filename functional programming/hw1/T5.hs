module HW1.T5
  ( joinWith
  , splitOn
  ) where

import Data.Foldable (Foldable (foldr'))
import Data.List.NonEmpty (NonEmpty ((:|)))

splitOn :: Eq a => a -> [a] -> NonEmpty [a]
splitOn sep = foldr split ([] :| [])
  where
    split c (x :| xs) | c == sep  = [] :| (x : xs)
                      | otherwise = (c : x) :| xs

joinWith :: a -> NonEmpty [a] -> [a]
joinWith sep (x :| xs) = x ++ foldr' sepJoin [] xs
  where
    sepJoin a b = sep : (a ++ b)
