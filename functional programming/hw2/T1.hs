module HW2.T1 (
    Option (..), 
    Pair (..),
    Quad (..),
    Annotated (..),
    Except (..), 
    Prioritised (..),
    Stream (..),
    List (..),
    Fun (..), 
    Tree (..),
    mapOption, 
    mapPair, 
    mapQuad, 
    mapAnnotated, 
    mapExcept, 
    mapPrioritised, 
    mapStream, 
    mapList, 
    mapFun, 
    mapTree) where

data Option a = None | Some a

mapOption :: (a -> b) -> (Option a -> Option b)
mapOption a None     = None
mapOption f (Some a) = Some (f a)

data Pair a = P a a

mapPair :: (a -> b) -> (Pair a -> Pair b)
mapPair f (P a b) = P (f a) (f b)

mapQuad :: (a -> b) -> (Quad a -> Quad b)

data Quad a = Q a a a a
mapQuad f (Q a b c d) = Q (f a) (f b) (f c) (f d)

data Annotated e a = a :# e
infix 0 :#

mapAnnotated :: (a -> b) -> (Annotated e a -> Annotated e b)
mapAnnotated f (a :# e) = f a :# e

data Except e a = Error e | Success a

mapExcept :: (a -> b) -> (Except e a -> Except e b)
mapExcept a (Error e)   = Error e
mapExcept f (Success a) = Success (f a)

data Prioritised a = Low a | Medium a | High a

mapPrioritised :: (a -> b) -> (Prioritised a -> Prioritised b)
mapPrioritised f (Low    a) = Low    (f a)
mapPrioritised f (Medium a) = Medium (f a)
mapPrioritised f (High   a) = High   (f a)

data Stream a = a :> Stream a
infixr 5 :>

mapStream :: (a -> b) -> (Stream a -> Stream b)
mapStream f (a :> b) = f a :> mapStream f b

data List a = Nil | a :. List a
infixr 5 :.

mapList :: (a -> b) -> (List a -> List b)
mapList _ Nil      = Nil
mapList f (a :. b) = f a :. mapList f b

data Fun i a = F (i -> a)

mapFun :: (a -> b) -> (Fun i a -> Fun i b)
mapFun f (F g) = F (f . g)

data Tree a = Leaf | Branch (Tree a) a (Tree a)

mapTree :: (a -> b) -> (Tree a -> Tree b)
mapTree a  Leaf          = Leaf
mapTree f (Branch l v r) = Branch (mapTree f l) (f v) (mapTree f r)