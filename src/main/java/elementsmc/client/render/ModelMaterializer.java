package elementsmc.client.render;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelMaterializer extends ModelBase
{
	ModelRenderer ground1;
    ModelRenderer ground2;
    ModelRenderer ground3;
    ModelRenderer ground4;
    ModelRenderer arm11;
    ModelRenderer arm12;
    ModelRenderer arm21;
    ModelRenderer arm31;
    ModelRenderer arm41;
    ModelRenderer arm22;
    ModelRenderer arm42;
    ModelRenderer arm32;
  
  public ModelMaterializer()
  {
    textureWidth = 64;
    textureHeight = 64;
    
      ground1 = new ModelRenderer(this, 0, 0);
      ground1.addBox(0F, 0F, 0F, 16, 2, 16);
      ground1.setRotationPoint(-8F, 0F, -8F);
      ground1.setTextureSize(64, 64);
      ground1.mirror = true;
      setRotation(ground1, 0F, 0F, 0F);
      ground2 = new ModelRenderer(this, 0, 18);
      ground2.addBox(0F, 0F, 0F, 14, 1, 14);
      ground2.setRotationPoint(-7F, -1F, -7F);
      ground2.setTextureSize(64, 64);
      ground2.mirror = true;
      setRotation(ground2, 0F, 0F, 0F);
      ground3 = new ModelRenderer(this, 0, 33);
      ground3.addBox(0F, 0F, 0F, 8, 1, 8);
      ground3.setRotationPoint(-4F, -1F, -4F);
      ground3.setTextureSize(64, 64);
      ground3.mirror = true;
      setRotation(ground3, 0F, 0F, 0F);
      ground4 = new ModelRenderer(this, 32, 36);
      ground4.addBox(0F, 0F, 0F, 5, 1, 5);
      ground4.setRotationPoint(-2.5F, -0.5F, -2.5F);
      ground4.setTextureSize(64, 64);
      ground4.mirror = true;
      setRotation(ground4, 0F, 0F, 0F);
      arm11 = new ModelRenderer(this, 0, 0);
      arm11.addBox(-0.5F, 0F, -0.5F, 1, 6, 1);
      arm11.setRotationPoint(-5F, -7F, -5F);
      arm11.setTextureSize(64, 64);
      arm11.mirror = true;
      setRotation(arm11, 0F, 0.7853982F, 0F);
      arm12 = new ModelRenderer(this, 4, 0);
      arm12.addBox(-0.5F, 0F, -1F, 1, 5, 1);
      arm12.setRotationPoint(-5.353F, -7F, -5.353F);
      arm12.setTextureSize(64, 64);
      arm12.mirror = true;
      setRotation(arm12, 2.146755F, 0.7853982F, 0F);
      arm21 = new ModelRenderer(this, 0, 0);
      arm21.addBox(-0.5F, 0F, -0.5F, 1, 6, 1);
      arm21.setRotationPoint(5F, -7F, -5F);
      arm21.setTextureSize(64, 64);
      arm21.mirror = true;
      setRotation(arm21, 0F, 0.7853982F, 0F);
      arm31 = new ModelRenderer(this, 0, 0);
      arm31.addBox(-0.5F, 0F, -0.5F, 1, 6, 1);
      arm31.setRotationPoint(5F, -7F, 5F);
      arm31.setTextureSize(64, 64);
      arm31.mirror = true;
      setRotation(arm31, 0F, 0.7853982F, 0F);
      arm41 = new ModelRenderer(this, 0, 0);
      arm41.addBox(-0.5F, 0F, -0.5F, 1, 6, 1);
      arm41.setRotationPoint(-5F, -7F, 5F);
      arm41.setTextureSize(64, 64);
      arm41.mirror = true;
      setRotation(arm41, 0F, 0.7853982F, 0F);
      arm22 = new ModelRenderer(this, 4, 0);
      arm22.addBox(-0.5F, 0F, -1F, 1, 5, 1);
      arm22.setRotationPoint(5.3535533F, -7F, -5.3535533F);
      arm22.setTextureSize(64, 64);
      arm22.mirror = true;
      setRotation(arm22, 2.146755F, -0.7853982F, 0F);
      arm42 = new ModelRenderer(this, 4, 0);
      arm42.addBox(-0.5F, 0F, 0F, 1, 5, 1);
      arm42.setRotationPoint(-5.3535533F, -7F, 5.3535533F);
      arm42.setTextureSize(64, 64);
      arm42.mirror = true;
      setRotation(arm42, -2.146755F, -0.7853982F, 0F);
      arm32 = new ModelRenderer(this, 4, 0);
      arm32.addBox(-0.5F, 0F, 0F, 1, 5, 1);
      arm32.setRotationPoint(5.3535533F, -7F, 5.3535533F);
      arm32.setTextureSize(64, 64);
      arm32.mirror = true;
      setRotation(arm32, -2.146755F, 0.7853982F, 0F);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    ground1.render(f5);
    ground2.render(f5);
    ground3.render(f5);
    ground4.render(f5);
    arm11.render(f5);
    arm12.render(f5);
    arm21.render(f5);
    arm31.render(f5);
    arm41.render(f5);
    arm22.render(f5);
    arm42.render(f5);
    arm32.render(f5);
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
