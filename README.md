# LemeHost Auto

App para Android que mantiene un servidor gratuito de [LemeHost](https://lemehost.com) activo, presionando automáticamente el botón "Extend time" del panel Free Plan a intervalos aleatorios (entre 8 y 27 minutos).

## Cómo funciona

La app carga el panel de LemeHost en un WebView que corre dentro de un servicio en primer plano (foreground service), para que Android no lo mate al apagar la pantalla. Como el login requiere captcha, hay que iniciar sesión manualmente una vez desde la propia app.

## Uso

1. Instalá el APK (ver [Releases](https://github.com/CraftPlay777/LemehostAuto/releases)).
2. Abrí la app e iniciá sesión en LemeHost dentro del WebView.
3. Tocá "Activar automatización".
4. En Ajustes → Apps → LemeHost Auto → Batería, desactivá la optimización de batería para que el servicio no se mate en segundo plano.

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
