Rails.application.routes.draw do
  devise_for :users
  root to: 'posts#index'
  patch '/update_avatar', to: 'users#update_avatar', as: :update_avatar
  patch '/update_cover', to: 'users#update_cover', as: :update_cover
  resources :users, only: [:index, :show] 
  resources :posts do
    post '/like', to: 'reactions#like', as: :like_action
    post '/dislike', to: 'reactions#dislike', as: :dislike_action
    resources :comments do
      post '/support', to: 'votes#support', as: :support_action
      post '/nonsupport', to: 'votes#nonsupport', as: :nonsupport_action
    end
  end
end
