{-# LANGUAGE DerivingStrategies         #-}
{-# LANGUAGE GeneralizedNewtypeDeriving #-}

module HW2.T6 (
  ParseError (..),
  Parser (..),
  runP,
  pChar,
  pEof,
  parseError
  ) where

import Control.Applicative
import Control.Monad
import Data.Char (digitToInt, isDigit, isSpace, isUpper)
import Data.Functor
import Data.Scientific
import GHC.Natural
import HW2.T1
import HW2.T4 (Expr(..), Prim(..))
import HW2.T5

data ParseError = ErrorAtPos Natural

newtype Parser a = P (ExceptState ParseError (Natural, String) a) deriving newtype (Functor, Applicative, Monad)

runP :: Parser a -> String -> Except ParseError a
runP (HW2.T6.P x) v = case runES x (0, v) of
                     Error e          -> Error e
                     Success (a :# _) -> Success a

pChar :: Parser Char
pChar = HW2.T6.P (ES (\(pos, s) ->
  case s of
    []     -> Error (ErrorAtPos pos)
    (c:cs) -> Success (c :# (pos + 1, cs))))

parseError :: Parser a
parseError = HW2.T6.P (ES (\(p, _) -> Error (ErrorAtPos p)))

instance Alternative Parser where
  empty = parseError
  (<|>) (HW2.T6.P a) (HW2.T6.P b)= HW2.T6.P (ES (\(pos, s) -> case runES a (pos, s) of
          Success x -> Success x
          Error _ -> runES b (pos, s)))

pEof :: Parser ()
pEof = HW2.T6.P (ES (\(p, s) ->
  case s of
    [] -> Success (() :# (p, []))
    _  -> Error (ErrorAtPos p)))