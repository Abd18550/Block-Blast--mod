BlockBlast Assistant (Android overlay companion)

What this is:
- A standalone Android app that overlays on top of Block Blast! (HungryStudio) to analyze classic 8x8 mode and suggest optimal placements.
- Uses Accessibility + MediaProjection (screen capture) to read the board and piece area; draws a translucent overlay with arrows/tiles.
- No direct modification of the original game APK.

Features planned:
- Calibration: let you mark grid top-left, cell size, and pieces area.
- Live analysis: capture frames, detect board occupancy/colors, detect 3 current pieces.
- Solver: choose placements to maximize cleared rows/columns and reduce fragmentation.
- Overlay: highlight suggested cells and show score estimate.

Build:
- Open the `blockblast-assistant` folder in Android Studio (Giraffe+).
- Build & install on Android 10+ (needs MediaProjection for screen capture and SYSTEM_ALERT_WINDOW for overlay).

Legal note:
- This tool provides guidance only and does not modify or redistribute the original game.
