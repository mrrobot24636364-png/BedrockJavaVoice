@echo off
where gradle >nul 2>nul
if %ERRORLEVEL% NEQ 0 (
  echo Gradle is not installed. Install Gradle 8.13+ or generate a standard wrapper.
  exit /b 127
)
gradle %*
