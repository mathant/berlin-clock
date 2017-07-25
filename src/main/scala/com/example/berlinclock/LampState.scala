package com.example.berlinclock

sealed trait LampState

case object O extends LampState
case object Y extends LampState
case object R extends LampState