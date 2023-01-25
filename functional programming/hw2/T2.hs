module HW2.T2 ( 
    distOption, 
    distPair, 
    distQuad, 
    distAnnotated, 
    distExcept, 
    distPrioritised, 
    distStream, 
    distList, 
    distFun, 
    wrapOption, 
    wrapPair, 
    wrapQuad, 
    wrapAnnotated, 
    wrapExcept, 
    wrapPrioritised, 
    wrapStream, 
    wrapList, 
    wrapFun,
    concatList) where

import HW2.T1

distOption :: (Option a, Option b) -> Option (a, b)
distOption (a, None)        = None
distOption (None, a)        = None
distOption (Some a, Some b) = Some (a, b)

distPair :: (Pair a, Pair b) -> Pair (a, b)
distPair (P a b, P c d) = P (a, c) (b, d)

distQuad :: (Quad a, Quad b) -> Quad (a, b)
distQuad (Q a b c d, Q e f g h) = Q (a, e) (b, f) (c, g) (d, h)

distAnnotated :: Semigroup e => (Annotated e a, Annotated e b) -> Annotated e (a, b)
distAnnotated (a :# e1, b :# e2) = (a, b) :# (e1 <> e2)

distExcept :: (Except e a, Except e b) -> Except e (a, b)
distExcept (Error e, a) = Error e
distExcept (a, Error e) = Error e
distExcept (Success a, Success b) = Success (a, b)

distPrioritised :: (Prioritised a, Prioritised b) -> Prioritised (a, b)
distPrioritised (Low a, Low b)       = Low (a, b)
distPrioritised (Low a, Medium b)    = Medium (a, b)
distPrioritised (Low a, High b)      = High (a, b)
distPrioritised (Medium a, Low b)    = Medium (a, b)
distPrioritised (Medium a, Medium b) = Medium (a, b)
distPrioritised (Medium a, High b)   = High (a, b)
distPrioritised (High a, Low b)      = High (a, b)
distPrioritised (High a, Medium b)   = High (a, b)
distPrioritised (High a, High b)     = High (a, b)

distStream :: (Stream a, Stream b) -> Stream (a, b)
distStream (a :> b, c :> d) = (a, c) :> distStream (b, d)

concatList :: List a -> List a -> List a
concatList Nil a      = a
concatList a Nil      = a
concatList (a :. b) c = a :. concatList b c

distList :: (List a, List b) -> List (a, b)
distList (a, Nil) = Nil
distList (Nil, a) = Nil
distList (h :. t, s) = concatList (add h s) (distList (t, s))
   where
     add :: a -> List b -> List (a, b)
     add a Nil        = Nil
     add a (h1 :. t1) = (a, h1) :. add a t1

distFun :: (Fun i a, Fun i b) -> Fun i (a, b)
distFun (F a, F b) = F (\i -> (a i, b i))

wrapOption :: a -> Option a
wrapOption = Some

wrapPair :: a -> Pair a
wrapPair a = P a a

wrapQuad :: a -> Quad a
wrapQuad a = Q a a a a

wrapAnnotated :: Monoid e => a -> Annotated e a
wrapAnnotated a = a :# mempty

wrapExcept :: a -> Except e a
wrapExcept = Success

wrapPrioritised :: a -> Prioritised a
wrapPrioritised = Low

wrapStream :: a -> Stream a
wrapStream a = a :> wrapStream a

wrapList :: a -> List a
wrapList a = a :. Nil

wrapFun :: a -> Fun i a
wrapFun a = F (const a)