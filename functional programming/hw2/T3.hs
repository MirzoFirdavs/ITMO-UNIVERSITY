module HW2.T3 ( 
    joinOption, 
    joinExcept, 
    joinAnnotated, 
    joinList, 
    joinFun) where

import HW2.T1
import HW2.T2 (concatList)


joinOption :: Option (Option a) -> Option a
joinOption (Some a) = a
joinOption a        = None

joinExcept :: Except e (Except e a) -> Except e a
joinExcept (Success a) = a
joinExcept (Error e)   = Error e

joinAnnotated :: Semigroup e => Annotated e (Annotated e a) -> Annotated e a
joinAnnotated ((a :# e1) :# e2) = a :# (e2 <> e1)

joinList :: List (List a) -> List a

joinList Nil      = Nil
joinList (a :. b) = concatList a (joinList b)

getFun :: Fun i a -> (i -> a)
getFun (F a) = a

joinFun :: Fun i (Fun i a) -> Fun i a
joinFun (F a) = F (\i -> getFun (a i) i)