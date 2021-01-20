import {initialState, newsletterReducer} from './newsletter.reducer';
import * as NewsletterActions from './newsletter.actions';
import {NewsletterModel} from '../../../models/newsletter.model';

describe('Newsletter Reducer', () => {

    describe('undefined action', () => {
      it('should return the default state', () => {
        const action = { type: 'NOOP' } as any;
        const result = newsletterReducer(undefined, action);

        expect(result).toEqual(initialState);
      });
    });

    describe('[Newsletter] Delete newsletter', () => {
      it('should delete newsletter', () => {
        const action = new NewsletterActions.DeleteNewsletter(1);
        const result = newsletterReducer(initialState, action);

        expect(result).toEqual({
          ...initialState,
          error: null,
          success: null
        });
      });
    });

    describe('[Newsletter] Newsletter fail', () => {
      it('should toggle error for newsletter', () => {
        const action = new NewsletterActions.NewsletterFail('An unknown error occurred!');
        const result = newsletterReducer(initialState, action);

        expect(result).toEqual({
          ...initialState,
          error: action.payload
        });
      });
    });

    describe('[Newsletter] Newsletter success', () => {
     it('should toggle success for newsletter', () => {
        const action = new NewsletterActions.NewsletterSuccess('Successful!');
        const result = newsletterReducer(initialState, action);

        expect(result).toEqual({
        ...initialState,
           success: action.payload
        });
      });
    });

    describe('[Newsletter] Add newsletter', () => {
        it('should start add newsletter', () => {
           const action = new NewsletterActions.AddNewsletter({ name: 'Title', description: 'Description',
            picture: 'img.jpg', publishedDate: new Date(), culturalOfferId: 1  });
           const result = newsletterReducer(initialState, action);

           expect(result).toEqual({
           ...initialState,
              bar: true
           });
         });
       });

    describe('[Newsletter] Update newsletter', () => {
      it('should start update newsletter', () => {
        const newsletter: NewsletterModel = {
            id: 1,
            name: 'TitleUpdated',
            description: 'Description',
            picture: 'img.jpg',
            publishedDate: new Date(),
            culturalOfferId : 1,
            culturaloffer: ''
        };
        const action = new NewsletterActions.UpdateNewsletter({ newsletter });
        const result = newsletterReducer(initialState, action);

        expect(result).toEqual({
        ...initialState,
            bar: true
        });
      });
    });

    describe('[Newsletter] Get newsletter success', () => {
      it('should get newsletter', () => {
        const newsletter = new NewsletterModel(1, 'Title', 'Description', new Date(), 1, 'img.jpg', '');
        const action = new NewsletterActions.GetNewsletterSuccess(newsletter);
        const result = newsletterReducer(initialState, action);

        const newsletterResult = new NewsletterModel(action.payload.id, action.payload.name,
            action.payload.description, action.payload.publishedDate, action.payload.culturalOfferId, action.payload.picture, '');
        expect(result).toEqual({
          ...initialState,
          newsletter: newsletterResult
        });
      });
    });

    describe('[Newsletter] Get newsletters success', () => {
        it('should get newsletters', () => {
          const newsletters = {content: [new NewsletterModel(1, 'Title', 'Description', new Date(), 1, 'img.jpg', '')]};
          const action = new NewsletterActions.GetNewslettersSuccess(newsletters);
          const result = newsletterReducer(initialState, action);

          expect(result).toEqual({
            ...initialState,
            newsletters: action.payload
          });
        });
      });

    describe('[Newsletter] Clear Error', () => {
      it('should clear error state for newsletter', () => {
        const action = new NewsletterActions.ClearError();
        const result = newsletterReducer(initialState, action);

        expect(result).toEqual({
          ...initialState,
          error: null
        });
      });
    });

    describe('[Newsletter] Clear Success', () => {
        it('should clear success state for newsletter', () => {
          const action = new NewsletterActions.ClearSuccess();
          const result = newsletterReducer(initialState, action);

          expect(result).toEqual({
            ...initialState,
            success: null
          });
        });
      });

    describe('[Newsletter] Get categories select success', () => {
        it('should get categories for category select', () => {
            const categories = {content: [{id: 1, name: 'Category1'}]};
            const action = new NewsletterActions.GetCategoriesSelectSuccess(categories);
            const result = newsletterReducer(initialState, action);

            expect(result).toEqual({
            ...initialState,
            categoriesSelect: action.payload
            });
        });
    });

    describe('[Newsletter] Get subcategories select success', () => {
        it('should get subcategories for subcategory select', () => {
            const subcategories = {content: [{id: 1, name: 'Subcategory1'}]};
            const action = new NewsletterActions.GetSubcategoriesSelectSuccess(subcategories);
            const result = newsletterReducer(initialState, action);

            expect(result).toEqual({
            ...initialState,
            subcategoriesSelect: action.payload,
            offersSelect: {content: [], numberOfElements: 0, totalElements: 0, totalPages: 0, number: 0}
            });
        });
    });

    describe('[Newsletter] Get offers select success', () => {
        it('should get cultural offers for cultural offer select', () => {
            const offers = {content: [{id: 1, name: 'CulturalOffer1'}]};
            const action = new NewsletterActions.GetOffersSelectSuccess(offers);
            const result = newsletterReducer(initialState, action);

            expect(result).toEqual({
            ...initialState,
            offersSelect: action.payload
            });
        });
    });

    describe('[Newsletter] Get categories subscribed success', () => {
        it('should categories for subscribed user', () => {
            const categories = {content: [{id: 1, name: 'Category1'}]};
            const action = new NewsletterActions.GetCategoriesSubscribedSuccess(categories);
            const result = newsletterReducer(initialState, action);

            expect(result).toEqual({
            ...initialState,
            categoriesSubscribed: action.payload
            });
        });
    });

    describe('[Newsletter] Get newsletters subscribed success', () => {
        it('should newsletters for subscribed user', () => {
            const newsletters = {content: [{id: 1, name: 'Newsletter1'}]};
            const action = new NewsletterActions.GetNewslettersSubscribedSuccess(newsletters);
            const result = newsletterReducer(initialState, action);

            expect(result).toEqual({
            ...initialState,
            newslettersSubscribed: action.payload
            });
        });
    });

    describe('[Newsletter] Unsubscribe', () => {
        it('should unsubscribe user from cultural offer', () => {
            const data = { idOffer: 1, idUser: 1 };
            const action = new NewsletterActions.Unsubscribe(data);
            const result = newsletterReducer(initialState, action);

            expect(result).toEqual({
            ...initialState,
            bar: true
            });
        });
    });

    describe('[Newsletter] Get categories select', () => {
        it('should start get categories for category select', () => {
            const data = { page: 1, size: 10 };
            const action = new NewsletterActions.GetCategoriesSelect(data);
            const result = newsletterReducer(initialState, action);

            expect(result).toEqual({
            ...initialState
            });
        });
    });

    describe('[Newsletter] Get subcategories select', () => {
        it('should start get subcategories for subcategory select', () => {
            const data = { page: 1, size: 10, category: 1 };
            const action = new NewsletterActions.GetSubcategoriesSelect(data);
            const result = newsletterReducer(initialState, action);

            expect(result).toEqual({
            ...initialState
            });
        });
    });

    describe('[Newsletter] Get offers select', () => {
        it('should start get cultural offers for cultural offer select', () => {
            const data = { page: 1, size: 10, subcategory: 1 };
            const action = new NewsletterActions.GetOffersSelect(data);
            const result = newsletterReducer(initialState, action);

            expect(result).toEqual({
            ...initialState
            });
        });
    });

    describe('[Newsletter] Get newsletter page', () => {
        it('should start get newsletters', () => {
            const data = { page: 1, size: 10 };
            const action = new NewsletterActions.GetNewsletterPage(data);
            const result = newsletterReducer(initialState, action);

            expect(result).toEqual({
            ...initialState
            });
        });
    });

    describe('[Newsletter] Get newsletter', () => {
        it('should start get newsletter', () => {
            const data = { id: 1 };
            const action = new NewsletterActions.GetNewsletter(data);
            const result = newsletterReducer(initialState, action);

            expect(result).toEqual({
            ...initialState
            });
        });
    });

    describe('[Newsletter] Get categories subscribed', () => {
        it('should start get categories for subscribed user', () => {
            const data = { id: 1 };
            const action = new NewsletterActions.GetCategoriesSubscribed(data);
            const result = newsletterReducer(initialState, action);

            expect(result).toEqual({
            ...initialState
            });
        });
    });

    describe('[Newsletter] Get newsletters subscribed', () => {
        it('should start get newsletters for subscribed user', () => {
            const data = { page: 1, size: 10, catId: 1, id: 1 };
            const action = new NewsletterActions.GetNewslettersSubscribed(data);
            const result = newsletterReducer(initialState, action);

            expect(result).toEqual({
            ...initialState
            });
        });
    });
});
