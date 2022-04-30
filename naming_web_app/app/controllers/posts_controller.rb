class PostsController < ApplicationController
  before_action :authenticate_user!
  
  def index
    @posts = Post.all.order('created_at DESC')
    @post = Post.new
   end

  def create
    @post = current_user.posts.build(post_params)

    if @post.save
      redirect_to request.referrer, notice: 'Your post was created! yahoo!'
    else
      redirect_to request.referrer, alert: 'Sorry, post was not created, for some reason'
    end
  end

  def edit
    @post = Post.find(params[:id])
  end

  def update
    if Post.find(params[:id]).update(post_params)
      redirect_to root_path, alert: 'post updated!'
    else
      redirect_to request.referrer, alert: 'some dumb thing happened'
    end
  end
  
  def destroy
    if Post.find(params[:id]).destroy
      redirect_to request.referrer, notice: 'post deleted'
    else
      redirect_to request.referrer, alert: 'some dumb thing happened'
    end
  end

  private

  def post_params
    params.require(:post).permit(:text, :image)
  end
end
