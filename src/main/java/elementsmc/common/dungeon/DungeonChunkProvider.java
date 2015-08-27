package elementsmc.common.dungeon;

import java.util.List;
import java.util.Random;

import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;

public class DungeonChunkProvider implements IChunkProvider {

	public World world;
	public Dungeon dungeon;
	public Random rand;
	
	public DungeonChunkProvider(World world, long seed, Dungeon dungeon)
	{
		this.world = world;
		this.rand = new Random(seed);
		this.dungeon = dungeon;
	}
	
	@Override
	public boolean chunkExists(int x, int y) {
		return true;
	}

	@Override
	public Chunk provideChunk(int x, int y) {
		Chunk chunk = new Chunk(world, x, y);
		chunk.generateSkylightMap();
		return chunk;
	}

	@Override
	public Chunk loadChunk(int x, int y) {
		return this.provideChunk(x, y);
	}

	@Override
	public void populate(IChunkProvider p_73153_1_, int p_73153_2_,
			int p_73153_3_) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean saveChunks(boolean p_73151_1_, IProgressUpdate p_73151_2_) {
		return true;
	}

	@Override
	public boolean unloadQueuedChunks() {
		return false;
	}

	@Override
	public boolean canSave() {
		return true;
	}

	@Override
	public String makeString() {
		return "DungeonLevelSource";
	}

	@Override
	public List getPossibleCreatures(EnumCreatureType p_73155_1_, int p_73155_2_, int p_73155_3_, int p_73155_4_) {
		return null;
	}

	@Override
	public ChunkPosition func_147416_a(World p_147416_1_, String p_147416_2_, int p_147416_3_, int p_147416_4_, int p_147416_5_) {
		return null;
	}

	@Override
	public int getLoadedChunkCount() {
		return 0;
	}

	@Override
	public void recreateStructures(int p_82695_1_, int p_82695_2_) {
		// TODO Auto-generated method stub

	}

	@Override
	public void saveExtraData() {

	}

}
