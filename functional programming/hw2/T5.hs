module HW2.T5 (
  ExceptState (..),
  mapExceptState,
  wrapExceptState,
  joinExceptState,
  modifyExceptState,
  throwExceptState, 
  EvaluationError (..), 
  eval
  ) where 

import Control.Monad
import HW2.T1
import HW2.T2
import HW2.T4 (Expr(..), Prim(..))

data ExceptState e s a = ES { runES :: s -> Except e (Annotated s a) }  

mapExceptState :: (a -> b) -> ExceptState e s a -> ExceptState e s b
mapExceptState f a = ES (mapExcept (mapAnnotated f) . runES a)

wrapExceptState :: a -> ExceptState e s a
wrapExceptState a = ES (\i -> wrapExcept (a :# i))

joinExceptState :: ExceptState e s (ExceptState e s a) -> ExceptState e s a
joinExceptState st = ES (\i ->
  case runES st i of
    Error e -> Error e
    Success (a :# b) -> runES a b)

modifyExceptState :: (s -> s) -> ExceptState e s ()
modifyExceptState f = ES (\s -> Success(() :# f s))

throwExceptState :: e -> ExceptState e s a
throwExceptState e = ES (\x -> Error e)

instance Functor (ExceptState e s) where
  fmap = mapExceptState

instance Applicative (ExceptState e s) where
  pure = wrapExceptState
  p <*> q = Control.Monad.ap p q

instance Monad (ExceptState e s) where
  m >>= f = joinExceptState (fmap f m)

data EvaluationError = DivideByZero

eval :: Expr -> ExceptState EvaluationError [Prim Double] Double
eval (Val x)        = pure x
eval (Op (Add x y)) = binaryOperation x y Add (+)
eval (Op (Sub x y)) = binaryOperation x y Sub (-)
eval (Op (Mul x y)) = binaryOperation x y Mul (*)
eval (Op (Abs x))   = unaryOperation x Abs abs
eval (Op (Sgn x))   = unaryOperation x Sgn signum
eval (Op (Div x y)) = do
  a <- eval x
  b <- eval y
  if b == 0
  then throwExceptState DivideByZero
  else modifyExceptState (Div a b:)
  pure (a / b)

binaryOperation :: Expr -> Expr -> (Double -> Double -> Prim Double) -> (Double -> Double -> Double) -> ExceptState EvaluationError [Prim Double] Double
binaryOperation x y op s = do
  a <- eval x
  b <- eval y
  modifyExceptState (op a b:)
  pure (a `s` b)

unaryOperation :: Expr -> (Double -> Prim Double) -> (Double -> Double) -> ExceptState EvaluationError [Prim Double] Double
unaryOperation x op s = do
  a <- eval x
  modifyExceptState (op a:)
  pure (s a)