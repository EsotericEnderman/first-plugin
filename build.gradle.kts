import xyz.jpenilla.resourcefactory.bukkit.BukkitPluginYaml

plugins {
  java
  `java-library`

  id("io.papermc.paperweight.userdev") version "1.7.1"
  id("xyz.jpenilla.resource-factory-bukkit-convention") version "1.1.1"
  id("xyz.jpenilla.run-paper") version "2.3.0"

  id("io.github.goooler.shadow") version "8.1.7"
}

val groupStringSeparator = "."
val kebabcaseStringSeparator = "-"
val snakecaseStringSeparator = "_"

fun capitalizeFirstLetter(string: String): String {
  return string.first().uppercase() + string.slice(IntRange(1, string.length - 1))
}

fun snakecase(string: String): String {
  return string.lowercase().replace(kebabcaseStringSeparator, snakecaseStringSeparator).replace(" ", snakecaseStringSeparator)
}

fun pascalcase(kebabcaseString: String): String {
  var pascalCaseString = ""

  val splitString = kebabcaseString.split(kebabcaseStringSeparator)

  for (part in splitString) {
    pascalCaseString += capitalizeFirstLetter(part)
  }

  return pascalCaseString
}

description = "My first Minecraft plugin."

val mainProjectAuthor = "Esoteric Enderman"
val projectAuthors = listOfNotNull(mainProjectAuthor)

val topLevelDomain = "dev"

group = topLevelDomain + groupStringSeparator + snakecase(mainProjectAuthor) + groupStringSeparator + snakecase(rootProject.name)
version = "1.0.0-SNAPSHOT"

val javaVersion = 21
val paperApiVersion = "1.21"

java {
  toolchain.languageVersion = JavaLanguageVersion.of(javaVersion)
}

repositories {
  mavenCentral()
  maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
  maven("https://repo.papermc.io/repository/maven-public/")
  maven("https://maven.enginehub.org/repo/")
  maven("https://repo.codemc.io/repository/maven-snapshots/")
  maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")
  maven("https://oss.sonatype.org/content/groups/public/")
  maven("https://jitpack.io")
}

dependencies {
  paperweight.paperDevBundle("$paperApiVersion-R0.1-SNAPSHOT")
  implementation("net.dv8tion", "JDA", "5.0.2")
  implementation("net.wesjd", "anvilgui", "1.10.0-SNAPSHOT")
  implementation("com.zaxxer", "HikariCP", "4.0.3")
  implementation("org.mongodb", "mongodb-driver-sync", "5.1.1")
  implementation("com.theokanning.openai-gpt3-java", "service", "0.18.2")
  implementation("org.bstats", "bstats-bukkit", "3.0.2")
  implementation("com.github.koca2000", "NoteBlockAPI", "1.6.2")
  compileOnly("com.sk89q.worldedit", "worldedit-core", "7.4.0-SNAPSHOT")
  compileOnly("me.clip", "placeholderapi", "2.11.6")
}

tasks {
  compileJava {
    options.release = javaVersion
  }

  javadoc {
    options.encoding = Charsets.UTF_8.name()
  }
}

bukkitPluginYaml {
  authors = projectAuthors

  main = project.group.toString() + groupStringSeparator + pascalcase(rootProject.name)
  apiVersion = paperApiVersion
  description = project.description

  depend.addAll("WorldEdit", "NoteBlockAPI")

  load = BukkitPluginYaml.PluginLoadOrder.POSTWORLD
}
