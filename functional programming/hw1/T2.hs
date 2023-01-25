module HW1.T2 
  ( N (..)
  , nplus
  , nmult
  , nsub
  , ncmp
  , nFromNatural
  , nToNum
  , nEven
  , nOdd
  , ndiv
  , nmod
  ) where

import Numeric.Natural

data N = Z | S N deriving (Show)

nplus :: N -> N -> N 
nplus Z Z = Z
nplus nm Z            = nm
nplus Z nm            = nm
nplus fiNum (S seNum) = S (nplus fiNum seNum)

nmult :: N -> N -> N
nmult Z Z             = Z
nmult nm Z            = Z
nmult Z nm            = Z
nmult (S Z) nm        = nm
nmult nm (S Z)        = nm
nmult fiNum (S seNum) = nplus fiNum (nmult fiNum seNum)

nsub :: N -> N -> Maybe N
nsub Z Z                 = Just Z
nsub nm Z                = Just nm 
nsub Z nm                = Nothing
nsub (S nm) Z            = Just (S nm)
nsub (S fiNum) (S seNum) = nsub fiNum seNum

ncmp :: N -> N -> Ordering
ncmp Z Z                 = EQ
ncmp nm Z                = GT
ncmp Z nm                = LT
ncmp (S fiNum) (S seNum) = ncmp fiNum seNum

nFromNatural :: Natural -> N
nFromNatural 0   = Z
nFromNatural nm  = S (nFromNatural (nm - 1))

nToNum :: Num a => N -> a
nToNum Z       = 0
nToNum (S nm)  = nToNum nm + 1

nEven :: N -> Bool
nEven Z       = True
nEven (S Z)   = False
nEven (S nm)  = not (nEven nm)

nOdd ::N -> Bool
nOdd Z       = False
nOdd (S Z)   = True
nOdd (S nm)  = not (nOdd nm)

sub :: N -> N -> N
sub Z (S nm)            = error "unexpected negative number"
sub nm Z                = nm
sub (S fiNum) (S seNum) = sub fiNum seNum

ndiv :: N -> N -> N
ndiv nm Z        = error "division by zero!"
ndiv Z nm        = Z
ndiv fiNum seNum = if ncmp fiNum seNum == LT then Z else S (ndiv (sub fiNum seNum) seNum)

nmod :: N -> N -> N 
nmod nm Z        = error "division by zero"
nmod Z nm        = Z
nmod fiNum seNum = if ncmp fiNum seNum == LT then fiNum else sub fiNum (nmult seNum (ndiv fiNum seNum))