# RSUtils

[![Jitpack](https://jitpack.io/v/RedSavant/RSUtils.svg)](https://jitpack.io/#RedSavant/RSUtils)

Librairie d'utilitaires pour plugins Paper/Bukkit.

## Installation

```kotlin
repositories {
    maven("https://jitpack.io")
}

dependencies {
    implementation("com.github.RedSavant:RSUtils:v1.0.0")
}
```

## Utilisation

### Colorize

Convertit du texte MiniMessage en `Component`.

```java
Component message = Colorize.colorize("<red>Hello</red>");
```

### PluginSetup

Enregistre listeners et commandes en une seule ligne.

```java
@Override
public void onEnable() {
    PluginSetup.setup(this,
        new MyListener(),
        new MyCommand()
    );
}
```

La classe de commande doit être annotée avec `@CommandInfo` :

```java
@CommandInfo("mycommand")
public class MyCommand implements CommandExecutor {
    // ...
}
```

## Licence

MIT
