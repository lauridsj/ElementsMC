package elementsmc.client.render;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelInfuser extends ModelBase {

	ModelRenderer bottom;
	ModelRenderer arm1;
	ModelRenderer hold1;
	ModelRenderer arm2;
	ModelRenderer hold2;
	ModelRenderer panel;
	ModelRenderer bottom2;
	ModelRenderer bottom3;
	ModelRenderer bottom4;

	public ModelInfuser() {
		textureWidth = 64;
		textureHeight = 64;

		bottom = new ModelRenderer(this, 0, 0);
		bottom.addBox(0F, 0F, 0F, 16, 2, 13);
		bottom.setRotationPoint(-8F, -2F, -5F);
		bottom.setTextureSize(64, 64);
		bottom.mirror = true;
		setRotation(bottom, 0F, 0F, 0F);
		arm1 = new ModelRenderer(this, 0, 0);
		arm1.addBox(0F, 0F, 0F, 1, 5, 1);
		arm1.setRotationPoint(-7.9F, -7F, -0.5F);
		arm1.setTextureSize(64, 64);
		arm1.mirror = true;
		setRotation(arm1, 0F, 0F, 0F);
		hold1 = new ModelRenderer(this, 4, 0);
		hold1.addBox(0F, -1F, -1F, 1, 2, 2);
		hold1.setRotationPoint(-7.9F, -7.8F, 0F);
		hold1.setTextureSize(64, 64);
		hold1.mirror = true;
		setRotation(hold1, 0.7853982F, 0F, 0F);
		arm2 = new ModelRenderer(this, 0, 0);
		arm2.addBox(0F, 0F, 0F, 1, 5, 1);
		arm2.setRotationPoint(6.9F, -7F, -0.5F);
		arm2.setTextureSize(64, 64);
		arm2.mirror = true;
		setRotation(arm2, 0F, 0F, 0F);
		hold2 = new ModelRenderer(this, 4, 0);
		hold2.addBox(0F, -1F, -1F, 1, 2, 2);
		hold2.setRotationPoint(6.9F, -7.8F, 0F);
		hold2.setTextureSize(64, 64);
		hold2.mirror = true;
		setRotation(hold2, 0.7853982F, 0F, 0F);
		panel = new ModelRenderer(this, 0, 19);
		panel.addBox(0F, 0F, 0F, 12, 0, 3);
		panel.setRotationPoint(-6F, -2F, -5F);
		panel.setTextureSize(64, 64);
		panel.mirror = true;
		setRotation(panel, -2.80998F, 0F, 0F);
		bottom2 = new ModelRenderer(this, 45, 0);
		bottom2.addBox(0F, 0F, 0F, 2, 2, 3);
		bottom2.setRotationPoint(-8F, -2F, -8F);
		bottom2.setTextureSize(64, 64);
		bottom2.mirror = true;
		setRotation(bottom2, 0F, 0F, 0F);
		bottom3 = new ModelRenderer(this, 45, 5);
		bottom3.addBox(0F, 0F, 0F, 2, 2, 3);
		bottom3.setRotationPoint(6F, -2F, -8F);
		bottom3.setTextureSize(64, 64);
		bottom3.mirror = true;
		setRotation(bottom3, 0F, 0F, 0F);
		bottom4 = new ModelRenderer(this, 0, 15);
		bottom4.addBox(0F, 0F, 0F, 12, 1, 3);
		bottom4.setRotationPoint(-6F, -1F, -8F);
		bottom4.setTextureSize(64, 64);
		bottom4.mirror = true;
		setRotation(bottom4, 0F, 0F, 0F);
	}

	public void render(Entity entity, float f, float f1, float f2, float f3,
			float f4, float f5) {
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		bottom.render(f5);
		arm1.render(f5);
		hold1.render(f5);
		arm2.render(f5);
		hold2.render(f5);
		panel.render(f5);
		bottom2.render(f5);
		bottom3.render(f5);
		bottom4.render(f5);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	public void setRotationAngles(float f, float f1, float f2, float f3,
			float f4, float f5, Entity ent) {
		super.setRotationAngles(f, f1, f2, f3, f4, f5, ent);
	}

}
