package elementsmc.client.render;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelIncinerator extends ModelBase
{

	
    ModelRenderer ground1;
    ModelRenderer ground2;
    ModelRenderer pillar1;
    ModelRenderer top1;
    ModelRenderer top2;
    ModelRenderer top3;
    ModelRenderer pillar2;
    ModelRenderer pillar3;
    ModelRenderer pillar4;
  
  public ModelIncinerator()
  {
    textureWidth = 64;
    textureHeight = 64;
    
      ground1 = new ModelRenderer(this, 0, 0);
      ground1.addBox(0F, 0F, 0F, 16, 2, 16);
      ground1.setRotationPoint(-8F, -1F, -8F);
      ground1.setTextureSize(64, 64);
      ground1.mirror = true;
      setRotation(ground1, 0F, 0F, 0F);
      ground2 = new ModelRenderer(this, 0, 32);
      ground2.addBox(0F, 0F, 0F, 13, 1, 13);
      ground2.setRotationPoint(-6.5F, -2F, -6.5F);
      ground2.setTextureSize(64, 64);
      ground2.mirror = true;
      setRotation(ground2, 0F, 0F, 0F);
      pillar1 = new ModelRenderer(this, 0, 0);
      pillar1.addBox(0F, 0F, 0F, 3, 8, 3);
      pillar1.setRotationPoint(-6F, -10F, -6F);
      pillar1.setTextureSize(64, 64);
      pillar1.mirror = true;
      setRotation(pillar1, 0F, 0F, 0F);
      top1 = new ModelRenderer(this, 0, 18);
      top1.addBox(0F, 0F, 0F, 13, 1, 13);
      top1.setRotationPoint(-6.5F, -11F, -6.5F);
      top1.setTextureSize(64, 64);
      top1.mirror = true;
      setRotation(top1, 0F, 0F, 0F);
      top2 = new ModelRenderer(this, 0, 46);
      top2.addBox(0F, 0F, 0F, 11, 1, 11);
      top2.setRotationPoint(-5.5F, -12F, -5.5F);
      top2.setTextureSize(64, 64);
      top2.mirror = true;
      setRotation(top2, 0F, 0F, 0F);
      top3 = new ModelRenderer(this, 48, 0);
      top3.addBox(0F, 0F, 0F, 4, 3, 4);
      top3.setRotationPoint(-2F, -15F, -2F);
      top3.setTextureSize(64, 64);
      top3.mirror = true;
      setRotation(top3, 0F, 0F, 0F);
      pillar2 = new ModelRenderer(this, 0, 0);
      pillar2.addBox(0F, 0F, 0F, 3, 8, 3);
      pillar2.setRotationPoint(-6F, -10F, 3F);
      pillar2.setTextureSize(64, 64);
      pillar2.mirror = true;
      setRotation(pillar2, 0F, 0F, 0F);
      pillar3 = new ModelRenderer(this, 0, 0);
      pillar3.addBox(0F, 0F, 0F, 3, 8, 3);
      pillar3.setRotationPoint(3F, -10F, 3F);
      pillar3.setTextureSize(64, 64);
      pillar3.mirror = true;
      setRotation(pillar3, 0F, 0F, 0F);
      pillar4 = new ModelRenderer(this, 0, 0);
      pillar4.addBox(0F, 0F, 0F, 3, 8, 3);
      pillar4.setRotationPoint(3F, -10F, -6F);
      pillar4.setTextureSize(64, 64);
      pillar4.mirror = true;
      setRotation(pillar4, 0F, 0F, 0F);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    ground1.render(f5);
    ground2.render(f5);
    pillar1.render(f5);
    top1.render(f5);
    top2.render(f5);
    top3.render(f5);
    pillar2.render(f5);
    pillar3.render(f5);
    pillar4.render(f5);
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
