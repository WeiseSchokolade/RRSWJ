# RRSWJ
RRSWJ renders stuff with java

## Usage
![Release](https://jitpack.io/v/WeiseSchokolade/RRSWJ.svg)
#### Maven
```xml
<repositories>
  <repository>
    <id>jitpack.io</id>
    <url>https://www.jitpack.io</url>
  </repository>
</repositories>
```
```xml
<dependencies>
  <dependency>
    <groupId>com.github.WeiseSchokolade</groupId>
    <artifactId>RRSWJ</artifactId>
    <version>master-SNAPSHOT</version>
  </dependency>
</dependencies>
```

## Example

Example: Open a window and draw a coordinate system.
```java
public class MathGrid extends Renderer {
	public static void main(String[] args) {
		Window window = new Window(new MathGrid(), "Math Grid");
		window.open();
	}

	@Override
	public void render(Graph g, double deltaTimeMS) {
		g.drawRect(0, 0, 1, 1);
	}
}
```
