{-# LANGUAGE LambdaCase    #-}
{-# LANGUAGE TypeOperators #-}

module HW0.T1 ( type (<->) (Iso)
              , flipIso
              , runIso
              , distrib
              , assocPair
              , assocEither
              ) where

data a <-> b = Iso (a -> b) (b -> a)

flipIso :: (a <-> b) -> (b <-> a)
flipIso (Iso f g) = Iso g f

runIso :: (a <-> b) -> (a -> b)
runIso (Iso f _) = f

distrib :: Either a (b, c) -> (Either a b, Either a c)
distrib (Left a)       = (Left a, Left a)
distrib (Right (b, c)) = (Right b, Right c)

assocPair :: (a, (b, c)) <-> ((a, b), c)
assocPair = Iso (\case (a, (b, c)) -> ((a, b), c)) (\case ((a, b), c) -> (a, (b, c)))

assocEither :: Either a (Either b c) <-> Either (Either a b) c
assocEither = Iso (\case
                    Left a -> Left (Left a)
                    (Right (Left b)) -> Left (Right b)
                    (Right (Right c)) -> Right c) 
                  (\case  
                    (Right c) -> Right (Right c)
                    (Left (Right b)) -> Right (Left b)
                    (Left (Left a)) -> Left a)