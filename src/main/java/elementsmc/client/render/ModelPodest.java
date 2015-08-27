package elementsmc.client.render;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelPodest extends ModelBase
{
	ModelRenderer stick;
    ModelRenderer arm1;
    ModelRenderer arm2;
    ModelRenderer arm4;
    ModelRenderer arm3;
  
  public ModelPodest()
  {
    textureWidth = 256;
    textureHeight = 16;
    
      stick = new ModelRenderer(this, 240, 0);
      stick.addBox(-1F, 2F, -1F, 2, 7, 2);
      stick.setRotationPoint(0F, 0F, 0F);
      stick.setTextureSize(256, 16);
      stick.mirror = true;
      setRotation(stick, 0F, 0F, 0F);
      arm1 = new ModelRenderer(this, 240, 0);
      arm1.addBox(-0.5F, 0F, -0.5F, 1, 3, 1);
      arm1.setRotationPoint(1F, 0F, 1F);
      arm1.setTextureSize(256, 16);
      arm1.mirror = true;
      setRotation(arm1, 0F, 0.7853982F, 0F);
      arm2 = new ModelRenderer(this, 240, 0);
      arm2.addBox(-0.5F, 0F, -0.5F, 1, 3, 1);
      arm2.setRotationPoint(1F, 0F, -1F);
      arm2.setTextureSize(256, 16);
      arm2.mirror = true;
      setRotation(arm2, 0F, 0.7853982F, 0F);
      arm4 = new ModelRenderer(this, 240, 0);
      arm4.addBox(-0.5F, 0F, -0.5F, 1, 3, 1);
      arm4.setRotationPoint(-1F, 0F, -1F);
      arm4.setTextureSize(256, 16);
      arm4.mirror = true;
      setRotation(arm4, 0F, 0.7853982F, 0F);
      arm3 = new ModelRenderer(this, 240, 0);
      arm3.addBox(-0.5F, 0F, -0.5F, 1, 3, 1);
      arm3.setRotationPoint(-1F, 0F, 1F);
      arm3.setTextureSize(256, 16);
      arm3.mirror = true;
      setRotation(arm3, 0F, 0.7853982F, 0F);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    stick.render(f5);
    arm1.render(f5);
    arm2.render(f5);
    arm4.render(f5);
    arm3.render(f5);
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z)
  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }
  
  public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity ent)
  {
    super.setRotationAngles(f, f1, f2, f3, f4, f5, ent);
  }
}
