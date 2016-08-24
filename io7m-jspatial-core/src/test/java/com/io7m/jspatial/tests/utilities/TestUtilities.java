package tests.utilities;

import com.io7m.jnull.NonNull;
import com.io7m.jspatial.tests.Cuboid;
import com.io7m.jspatial.tests.Rectangle;
import com.io7m.jtensors.VectorI2I;
import com.io7m.jtensors.VectorI3I;

public final class TestUtilities
{
  private static final Object z = null;

  @SuppressWarnings({ "null", "unchecked" }) static @NonNull public
  <A>
  A
  actuallyNull()
  {
    return (A) TestUtilities.z;
  }

  public static Cuboid[] makeCuboids(
    final long id_first,
    final int container_size)
  {
    final Cuboid[] cubes = new Cuboid[8];
    final int half_size = container_size >> 1;
    final int cube_size = half_size - 3;

    int x_root = 2;
    int y_root = 2;
    int z_root = 2;
    cubes[0] =
      new Cuboid(
        id_first,
        new VectorI3I(x_root, y_root, z_root),
        new VectorI3I(x_root + cube_size, y_root + cube_size, z_root
          + cube_size));

    x_root = half_size + 2;
    y_root = 2;
    z_root = 2;
    cubes[1] =
      new Cuboid(
        id_first + 1,
        new VectorI3I(x_root, y_root, z_root),
        new VectorI3I(x_root + cube_size, y_root + cube_size, z_root
          + cube_size));

    x_root = 2;
    y_root = half_size + 2;
    z_root = 2;
    cubes[2] =
      new Cuboid(
        id_first + 2,
        new VectorI3I(x_root, y_root, z_root),
        new VectorI3I(x_root + cube_size, y_root + cube_size, z_root
          + cube_size));

    x_root = half_size + 2;
    y_root = half_size + 2;
    z_root = 2;
    cubes[3] =
      new Cuboid(
        id_first + 3,
        new VectorI3I(x_root, y_root, z_root),
        new VectorI3I(x_root + cube_size, y_root + cube_size, z_root
          + cube_size));

    //
    // Upper Z
    //

    x_root = 2;
    y_root = 2;
    z_root = half_size + 2;
    cubes[4] =
      new Cuboid(
        id_first + 4,
        new VectorI3I(x_root, y_root, z_root),
        new VectorI3I(x_root + cube_size, y_root + cube_size, z_root
          + cube_size));

    x_root = half_size + 2;
    y_root = 2;
    z_root = half_size + 2;
    cubes[5] =
      new Cuboid(
        id_first + 5,
        new VectorI3I(x_root, y_root, z_root),
        new VectorI3I(x_root + cube_size, y_root + cube_size, z_root
          + cube_size));

    x_root = 2;
    y_root = half_size + 2;
    z_root = half_size + 2;
    cubes[6] =
      new Cuboid(
        id_first + 6,
        new VectorI3I(x_root, y_root, z_root),
        new VectorI3I(x_root + cube_size, y_root + cube_size, z_root
          + cube_size));

    x_root = half_size + 2;
    y_root = half_size + 2;
    z_root = half_size + 2;
    cubes[7] =
      new Cuboid(
        id_first + 7,
        new VectorI3I(x_root, y_root, z_root),
        new VectorI3I(x_root + cube_size, y_root + cube_size, z_root
          + cube_size));

    return cubes;
  }

  public static Rectangle[] makeRectangles(
    final long id_first,
    final int container_size)
  {
    final Rectangle[] rectangles = new Rectangle[4];
    final int half_size = container_size >> 1;
    final int rectangle_size = half_size - 3;

    int x_root = 2;
    int y_root = 2;
    rectangles[0] =
      new Rectangle(id_first, new VectorI2I(x_root, y_root), new VectorI2I(
        x_root + rectangle_size,
        y_root + rectangle_size));

    x_root = half_size + 2;
    y_root = 2;
    rectangles[1] =
      new Rectangle(
        id_first + 1,
        new VectorI2I(x_root, y_root),
        new VectorI2I(x_root + rectangle_size, y_root + rectangle_size));

    x_root = 2;
    y_root = half_size + 2;
    rectangles[2] =
      new Rectangle(
        id_first + 2,
        new VectorI2I(x_root, y_root),
        new VectorI2I(x_root + rectangle_size, y_root + rectangle_size));

    x_root = half_size + 2;
    y_root = half_size + 2;
    rectangles[3] =
      new Rectangle(
        id_first + 3,
        new VectorI2I(x_root, y_root),
        new VectorI2I(x_root + rectangle_size, y_root + rectangle_size));

    return rectangles;
  }
}
