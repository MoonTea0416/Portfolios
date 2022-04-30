class UpdateReactionTable < ActiveRecord::Migration[6.1]
  def change
    add_column :reactions, :like, :boolean
    add_column :reactions, :dislike, :boolean
  end
end
