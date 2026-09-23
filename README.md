# LemeHost Auto

App para Android que mantiene un servidor gratuito de [LemeHost](https://lemehost.com) activo, presionando automáticamente los botones "Extend time" y "Start" de su panel Free Plan.

## Funciones

- **Extend time automático**, en un intervalo totalmente aleatorio entre 5-28 minutos y 1-60 segundos (evita repetir valores parecidos entre sí), para que no sea un patrón predecible.
- **Start automático** cada 30 minutos y 2 segundos exactos, por si el servidor se apagó solo.
- Corre como **foreground service** con wake lock, para sobrevivir con la pantalla apagada.
- Notificación persistente con cuenta regresiva y contador de repeticiones, expandible con un botón para **detener la automatización**.
- **URL del servidor y URL de apertura configurables** desde un menú de ayuda (botón "?"), con explicación de cómo conseguir cada una — no viene atada a ningún servidor en particular.
- Modo oscuro / claro configurable, y tipografía personalizada.

## Cómo funciona

La app carga el panel de LemeHost en un WebView que corre dentro del foreground service. Como el login requiere captcha, hay que iniciar sesión manualmente una vez desde la propia app.

## Uso

1. Instalá el APK (ver [Releases](https://github.com/CraftPlay777/LemehostAuto/releases)).
2. Abrí la app e iniciá sesión en LemeHost dentro del WebView.
3. Tocá el botón "?" y configurá la URL exacta de tu servidor (Extend time / Start).
4. Tocá "Activar automatización".
5. En Ajustes → Apps → LemeHost Auto → Batería, desactivá la optimización de batería para que el servicio no se mate en segundo plano.

## Compilar desde el código fuente

El proyecto se compila sin Gradle, usando `aapt2`, `javac`, `d8` y `uber-apk-signer` directamente (pensado para Termux):

```bash
./build_lemehost_auto.sh
```

El APK firmado queda en `output/`.

## Aviso

Sin afiliación oficial con LemeHost. Es una herramienta personal para automatizar una tarea repetitiva del panel de usuario.

## Licencia

MIT — ver [LICENSE](LICENSE).

## Autor

Juha ([CraftPlay777](https://github.com/CraftPlay777))
