# Dieta (Android)

App Android semplice in Jetpack Compose con due sezioni:
- Ricette settimanali (lun–ven, cena del giovedì saltata)
- Lista della spesa settimanale, raggruppata per categorie

## Requisiti
- Android Studio (Giraffe o più recente)
- JDK 17
- SDK Android (API 34 consigliata)

## Avvio locale
1. Apri la cartella del progetto in Android Studio
2. Sincronizza Gradle e builda l’app
3. Esegui su emulatore o dispositivo fisico

> Suggerimento: puoi impostare l’etichetta dell’app via env `APP_NAME`.

### Variabili d’ambiente
- `APP_NAME`: nome visualizzato dell’app (default: `Dieta`).

Per sviluppo locale puoi creare un file `.env` (facoltativo) copiando da `.env.example` e impostare `APP_NAME`. In CI l’azione usa `env`/`vars` del repository.

## GitHub Actions (CI)
Il workflow `Android CI`:
- Installa JDK e Android SDK
- Scarica Gradle 8.7, genera il wrapper ed esegue la build
- Lancia i test unitari
- Pubblica l’APK di debug come artifact

Percorso workflow: `.github/workflows/android.yml`.

Per personalizzare il nome app in CI, definisci una Repository Variable `APP_NAME` (Settings → Secrets and variables → Actions → Variables).

## Struttura principale
- `app/src/main/java/com/filippo/dieta/` → codice Kotlin (UI + dati)
- `app/src/main/res/` → risorse (stringhe, ecc.)
- `app/build.gradle.kts` → mod config, Compose, dipendenze

## Note
- Dati e ricette sono caricati localmente (nessuna API). 
- Se vorrai aggiungere persistenza o backend, potremo integrare Room/Retrofit.
