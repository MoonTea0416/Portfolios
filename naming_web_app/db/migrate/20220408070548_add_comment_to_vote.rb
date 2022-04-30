class AddCommentToVote < ActiveRecord::Migration[6.1]
  def change
    add_reference :votes, :comment, index: true
  end
end
