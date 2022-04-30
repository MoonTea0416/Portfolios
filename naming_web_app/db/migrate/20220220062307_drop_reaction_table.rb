class DropReactionTable < ActiveRecord::Migration[6.1]
  def up
    drop_table :reactions
  end

  def down
    raise ActiveRecord::IrreversibleMigration
  end
end
